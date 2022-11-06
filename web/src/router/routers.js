import {createRouter, createWebHashHistory} from "vue-router";
import Layout from '../layout/index'
import Home from '../views/Home'

export const routerMap = [
    {
        path: '/login',
        name: 'login',
        component: () => import('../login')
    },
    {
        path: '/401',
        name: '401',
        component: () => import('../views/error/401')
    },
    {
        path: '/404',
        name: '404',
        component: () => import('../views/error/404')
    },
    {
        path: "/:pathMatch(.*)*",
        name: "notFound",
        component: () => import('../views/error/404'),  // 引入 组件
    },

    {
        path: '/Layout',
        name: 'Layout',
        component: Layout,
        children: [
            {
                path: '/home',
                name: '首页',
                component: Home
            }
        ]
    }
]

//  创建路由
const routers = createRouter({
    history: createWebHashHistory(),
    routes: routerMap
})

export default routers