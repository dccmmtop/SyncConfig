## 简介
监控文件或目录变化,然后备份到指定目录中，或者上传到云端

## 使用方法
1. 配置 HOME 或者 USERPROFILE 环境变量
2. 在 `$HOME/.config/syncConfig.yaml` 中配置要监听的目录或文件，和备份目录
例如:
```yml
# 要监控的目录或者文件
abFilePathList:
  - C:\Users\Administrator\.ideavimrc
  - C:/Users/Administrator/AppData/Local/nvim
  - C:/Users/Administrator/.config/lsd/
# 本地备份目录
configBakPath: C:/Users/Administrator/code/configBak
```

## 扩展

在 `top.dc.handle` 包下实现`ConfigHandle` 方法， 该程序会自动扫描 `top.dc.handle` 包下的类, 然后根据类中的 `select` 方法返回值来决定是否对变动的文件进行处理

添加新的被监控文件时，只需新写类即可，无需改动代码，符合开闭原则
