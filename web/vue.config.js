const http = 'localhost:8415'
const { defineConfig } = require('@vue/cli-service')

module.exports = defineConfig({
    //  输出目录
    outputDir: '../src/main/resources/dist',
    //  静态资源使用相对路径
    publicPath: './',
    //  代理配置
    devServer: {
        host: '0.0.0.0',
        port: '3000',
        proxy: {
            '/api': {
                target: `http://${http}`,
                changeOrigin: true,
                pathRewrite: {
                    '^/api': ''
                },
                timeout: 60 * 1000
            }
        }
    },
    configureWebpack: {

    },
    chainWebpack: (config) => {
        config.plugin('html').tap((args) => {
            args[0].title = "EMS管理系统";
            args[0].build = new Date().getTime();
            args[0].env = process.env.NODE_ENV;
            return args;
        })
    },
    transpileDependencies: true,
    lintOnSave: false
})