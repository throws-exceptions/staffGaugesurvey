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

Vue.use(vueEventCalendar, {locale: 'zh', color: '#ffc09f'})
Vue.prototype.$Cookies = Cookies
axios.defaults.withCredentials = true
axios.defaults.baseURL = 'http://localhost:8081/'
Vue.prototype.$http = axios
Vue.config.productionTip = false

new Vue({
  router,
  render: h => h(App)
}).$mount('#app')
