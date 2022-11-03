import Vue from 'vue'
import ElementUI from 'element-ui'
import 'element-ui/lib/theme-chalk/index.css'
import App from './App.vue'
import router from "./router/routers";
import store from "./store";

import './assets/css/public.css'
import './assets/iconfont/iconfont.css'

import './utils/message'
import './router/index'
import './utils/common'

Vue.config.productionTip = false

Vue.use(ElementUI, {size: 'mini'})

new Vue({
  ElementUI,
  router,
  store,
  render: h => h(App),
}).$mount('#app')
