package top.dc;

import lombok.Data;
import org.apache.commons.io.FileUtils;
import org.tinylog.Logger;
import org.yaml.snakeyaml.Yaml;
import top.dc.handle.ConfigHandle;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.Objects;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

/**
 * @author dc on 2023/4/16
 */
@Data
public class Config {
    private List<String> abFilePathList;
    private List<ConfigHandle> configHandleList = new ArrayList<>();
    private String configBakPath;
    private boolean loaded;
    private static Config config = new Config();
    private Config(){}

    public static Config getInstance() throws NotFoundEnvArg, IOException, ClassNotFoundException, InstantiationException, IllegalAccessException {
        if(!config.loaded){
            return loadConfig();
        }
        return config;
    }

    public static Config loadConfig() throws IOException, NotFoundEnvArg, ClassNotFoundException, InstantiationException, IllegalAccessException {
        Yaml yaml = new Yaml();
        String home = System.getenv("HOME");
        if(null == home || home.isEmpty()){
            home = System.getenv("USERPROFILE");
        }
        if(null == home || home.isEmpty()){
            Logger.info("未找到系统变量 HOME 或者 USERPROFILE");
            throw new NotFoundEnvArg();
        }
        String yamlPath = String.join(File.separator,home,".config","syncConfig.yaml");
        InputStream inputStream = Files.newInputStream(Paths.get(yamlPath));
        config = yaml.loadAs(inputStream, Config.class);
        String jarPath = Config.class.getResource("").getPath();
        Logger.info("jarPath: " + jarPath);
        // 加载 handle 包下的所有类
        List<Class<?>> classList = new ArrayList<>();
        String scanPackageName = "top.dc.handle";
        if(jarPath.contains(".jar")){
            // 从 jar 中扫描
            Logger.info("从jar包中扫描");
            classList =  getClassListFromJarFile(scanPackageName);
        }else{
            // 从目录中扫描
            Logger.info("从目录中扫描");
            classList = getClassListFromDir(scanPackageName);
        }

        for (Class<?> klass : classList) {
            config.configHandleList.add((ConfigHandle) klass.newInstance());

        }


        if(!new File(config.getConfigBakPath()).exists()){
            FileUtils.forceMkdir(new File(config.getConfigBakPath()));
        }

        // 对象已经加载完毕
        config.setLoaded(true);
        return config;

    }

    public ConfigHandle find(String path) throws NotFoundConfigHandle {
        for (ConfigHandle configHandle : this.getConfigHandleList()) {
            if(configHandle.select(path)){
                return configHandle;
            }
        }
        throw new NotFoundConfigHandle("没有找到合适的配置变动处理器: " + path);
    }


    public static List<Class<?>> getClassListFromDir(String packagePath) {
        String localPath = Config.class.getClassLoader().getResource(packagePath.replace(".","/")).getPath();
        File classFile = new File(localPath );
        List<Class<?>> klassList = new ArrayList<>();
        for (File file : Objects.requireNonNull(classFile.listFiles())) {
            try {
                Class<?> klass = Class.forName(packagePath + "." + file.getName().replace(".class","") );
                Logger.info("加载配置处理器: " + klass.getName());
                if(klass.isInterface()){
                    continue;
                }
                klassList.add(klass);
            } catch (ClassNotFoundException e) {
                Logger.info("加载类失败: "+ e.getMessage());
            }
        }
        return klassList;
    }

    /**
     * 扫描 jar包中的文件获取class
     * 需要特殊的工具读取jar包中内容，不能向读取目录一样
     * @param packagePath 要扫描的包路径
     * @return 类对象
     */
    public static List<Class<?>> getClassListFromJarFile(String packagePath) {
        // 得到 jar 包的位置
        String jarPath = Config.class.getProtectionDomain().getCodeSource().getLocation().getPath();
        List<Class<?>> klassList = new ArrayList<>();

        JarFile jarFile = null;
        try {
            jarFile = new JarFile(jarPath);
        } catch (IOException e) {
            Logger.info(e.getMessage());
        }

        List<JarEntry> jarEntryList = new ArrayList<JarEntry>();

        Enumeration<JarEntry> ee = jarFile.entries();
        packagePath = packagePath.replace(".","/");
        while (ee.hasMoreElements()) {
            JarEntry entry = ee.nextElement();
            // 过滤我们出满足我们需求的东西
            if (entry.getName().startsWith(packagePath) && entry.getName().endsWith(".class")) {
                jarEntryList.add(entry);
            }
        }
        for (JarEntry entry : jarEntryList) {
            String className = entry.getName().replace('/', '.');
            className = className.substring(0, className.length() - 6);
            // 也可以采用如下方式把类加载成一个输入流
            // InputStream in = jarFile.getInputStream(entry);
            try {

                Logger.info("加载配置处理器: " + className);
                Class<?> klass = Thread.currentThread().getContextClassLoader().loadClass(className);
                if(klass.isInterface()){
                    continue;
                }
                klassList.add(klass);
            } catch (ClassNotFoundException e) {
                Logger.info("加载类失败: " + e.getMessage());
            }
        }

        return klassList;
    }
}
