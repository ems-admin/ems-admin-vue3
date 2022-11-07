import {defineStore} from "pinia";

export const useStore = defineStore('ems', {
    persist: true,
    state: () => {
        return {
            token: null,
            //  刷新token
            refreshToken: null,
            //  当前登录用户信息
            userInfo: null,
            //  是否已拉取用户菜单
            isLoadMenu: false,
            //  所有菜单（系统+用户动态授权）
            routers: null,
            //  权限列表
            permission: null,
            //  当前激活菜单
            activeIndex: '首页',
            //  所有已打开的菜单
            openTabs: []
        }
    },
    actions: {
        //  是否拉取用户菜单
        loadMenuAction(payload){
            this.isLoadMenu = payload
        },
        //  缓存用户菜单列表
        routerAction(payload){
            this.routers = payload
        },
        //  缓存用户权限
        permissionAction(payload){
            this.permission = payload
        },
        //  缓存用户token
        tokenAction(payload){
            this.token = payload
        },
        //  当前激活菜单
        activeIndexAction(payload){
            this.activeIndex = payload
        },
        //  添加已打开的菜单
        addTabAction(payload){
            //  如果不存在才添加
            if (this.openTabs.filter(tab => tab.name === payload.name).length === 0){
                payload.isClose = payload.name !== '首页'
                this.openTabs.push(payload)
            }
        },
        //  关闭已打开的菜单
        removeTabAction(payload){
            this.openTabs = this.openTabs.filter((tab) => tab.name !== payload)
        },
        //  清空所有tabs
        clearTabAction(){
            this.openTabs = []
        }
    }
})