import Vue from 'vue'
import App from './App.vue'
import router from './router'
import './plugins/element.js'
import './assets/css/global.css'
import './assets/icons/iconfont.css'
import axios from 'axios'
import 'vue-event-calendar/dist/style.css'
import vueEventCalendar from 'vue-event-calendar'
import Cookies from 'js-cookie'
import 'xe-utils'
import VXETable from 'vxe-table'
import 'vxe-table/lib/index.css'

Vue.use(VXETable)

// 给 vue 实例挂载全局窗口对象
Vue.prototype.$XModal = VXETable.modal

Vue.use(vueEventCalendar, {locale: 'zh', color: '#ffc09f'})
Vue.prototype.$Cookies = Cookies
axios.defaults.withCredentials = true
axios.defaults.baseURL = 'http://192.168.0.103:8083/'
Vue.prototype.$http = axios
Vue.config.productionTip = false

new Vue({
  router,
  render: h => h(App)
}).$mount('#app')
