import Vue from "vue";
import VueRouter from "vue-router";
import Layout from '../layout/index'
import Home from '../views/Home'

const originalPush = VueRouter.prototype.push

VueRouter.prototype.push = function push(location){
    return originalPush.call(this, location).catch(err => err)
}
Vue.use(VueRouter)

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
const routers = new VueRouter({
    mode: 'hash',
    routes: routerMap
})

export default routers