import { createApp } from 'vue'
import ElementPlus from 'element-plus'
import {createPinia} from "pinia";
import 'element-plus/dist/index.css'
import App from './App.vue'
import router from "./router/routers";

import './assets/css/public.css'
import './assets/iconfont/iconfont.css'

import './utils/message'
import './router/index'
import './utils/common'

const app = createApp(App)

app.use(createPinia())
app.use(ElementPlus, {size: 'small'})
app.use(router)
// Vue.config.productionTip = false
//
// Vue.use(ElementPlus, {size: 'mini'})
//
// new Vue({
//   ElementPlus,
//   router,
//   store,
//   render: h => h(App),
// }).$mount('#app')
