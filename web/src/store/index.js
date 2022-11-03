import Vue from "vue";
import Vuex from 'vuex'
import createPersistedState from "vuex-persistedstate"

Vue.use(Vuex)

const store = new Vuex.Store({
    state: {
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
    },
    mutations: {
        //  是否拉取用户菜单
        loadMenuMutation(state, payload){
            state.isLoadMenu = payload
        },
        //  缓存用户菜单列表
        routerMutation(state, payload){
            state.routers = payload
        },
        //  缓存用户权限列表
        permissionMutation(state, payload){
            state.permission = payload
        },
        //  缓存用户token
        tokenMutation(state, payload){
            state.token = payload
        },
        //  缓存刷新token
        refreshMutation(state, payload){
            state.refreshToken = payload
        },
        //  缓存当前登录用户信息
        userInfoMutation(state, payload){
            state.userInfo = payload
        },
        //  当前激活菜单
        activeMutation(state, payload){
            state.activeIndex = payload
        },
        //  添加已打开的菜单
        addTabMutation(state, payload){
            //  如果不存在才添加
            if (state.openTabs.filter(tab => tab.name === payload.name).length === 0){
                payload.isClose = payload.name !== '首页'
                state.openTabs.push(payload)
            }
        },
        //  关闭已打开的菜单
        removeTabMutation(state, payload){
            state.openTabs = state.openTabs.filter((tab) => tab.name !== payload)
        },
        //  清空所有tabs
        clearTabsMutation(state){
            state.openTabs = []
        }
    },
    actions: {
        //  是否拉取用户菜单
        loadMenuAction(context, data){
            context.commit('loadMenuMutation', data)
        },
        //  缓存用户菜单列表
        routerAction(context, data){
            context.commit('routerMutation', data)
        },
        //  缓存用户权限
        permissionAction(context, data){
            context.commit('permissionMutation', data)
        },
        //  缓存用户token
        tokenAction(context, data){
            context.commit('tokenMutation', data)
        },
        //  缓存用户刷新token
        refreshAction(context, data){
            context.commit('refreshMutation', data)
        },
        //  缓存当前登录用户信息
        userInfoAction(context, data){
            context.commit('userInfoMutation', data)
        },
        //  当前激活菜单
        activeIndexAction(context, data){
            context.commit('activeMutation', data)
        },
        //  添加已打开的菜单
        addTabAction(context, data){
            context.commit('addTabMutation', data)
        },
        //  关闭已打开的菜单
        removeTabAction(context, data){
            context.commit('removeTabMutation', data)
        },
        //  清空所有tabs
        clearTabAction(context, data){
            context.commit('clearTabsMutation', data)
        }

    },
    plugins: [createPersistedState({storage: sessionStorage})]
})

export default store