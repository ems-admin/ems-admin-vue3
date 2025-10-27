import { defineConfig } from 'vite'
import vue from '@vitejs/plugin-vue'
import path from 'path'
const http = 'localhost:8415'
export default defineConfig({
    plugins: [vue()],
    base: '/',         // 相当于 publicPath
    build: {
        outDir: '../src/main/resources/dist'          // 相当于 outputDir
    },
    server: {
        port: 3000,
        open: true,
        strictPort: true,
        proxy: {
            '/api': {
                target: 'http://localhost:8415',
                changeOrigin: true,
                rewrite: path => path.replace(/^\/api/, ''),
                timeout: 60 * 1000
            },
            '/auth': {
                target: 'http://localhost:8415',
                changeOrigin: true,
                rewrite: path => path.replace(/^\/auth/, '/auth'),
                timeout: 60 * 1000
            }
        }
    },
    resolve: {
        alias: {
            '@': path.resolve(__dirname, './src')
        }
    }
})
