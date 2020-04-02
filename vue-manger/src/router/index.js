import Vue from 'vue'
import VueRouter from 'vue-router'
import Login from '../components/Login.vue'
import Index from '../components/Index'
import Register from '../components/Register'
import UserManager from '../components/Users/UserManager'

Vue.use(VueRouter)

const router = new VueRouter({
  model: history,
  routes: [
    {
      path: '/',
      redirect: '/login'
    },
    {
      path: '/login',
      component: Login
    },
    {
      path: '/index',
      component: Index,
      children: [{ path:'/user/manger',component: UserManager}]
    },
    {
      path: '/register',
      component: Register
    }
  ]
})

export default router
