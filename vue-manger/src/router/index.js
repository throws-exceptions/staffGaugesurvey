import Vue from 'vue'
import VueRouter from 'vue-router'
import Login from '../components/Login.vue'
import Index from '../components/Index'
import Register from '../components/Register'
import UserManager from '../components/Users/UserManager'
import Dashboard from '../components/Dashboard';
import Distinguish from "../components/Cargo/Distinguish";
import CargoManager from "../components/Cargo/CargoManager";
import Cargo from "../components/Cargo/Cargo";

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
      redirect: '/dashboard',
      component: Index,
      meta: {title: '首页'},
      children: [
        {
          path: '/dashboard',
          component: Dashboard,
          meta: {title: '系统首页'}
        },
        {
          path: '/user/manger',
          component: UserManager,
          meta: {title: '用户管理'}
        },
        {
          path: '/distinguish',
          component: Distinguish,
          meta: {title: '识别系统'}
        },
        {
          path: '/cargoManger',
          component: CargoManager,
          meta: {title: '货物统计'}
        },
        {
          path: '/cargo',
          component: Cargo,
          meta: {title: '货物个人统计'}
        }
      ]

    },
    {
      path: '/register',
      component: Register
    }
  ]
})

export default router
