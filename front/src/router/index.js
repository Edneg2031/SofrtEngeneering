import Vue from 'vue'
import VueRouter from 'vue-router'
import Login from "@/views/Login.vue"
import Register from "@/views/Register.vue"
import ChangePassword from "@/views/ChangePassword.vue";
import Home from "@/views/Home.vue";
import UserManage from "@/components/UserManage.vue";
Vue.use(VueRouter)

const routes = [
    // 登录界面
  {
    path:'/',
    name:'login',
    component:Login
  },
    // 注册界面
  {
    path:'/register',
    name:'register',
    component:Register
  },
    // 忘记密码界面
  {
    path:'/changePassword',
    name:'changePassword',
    component:ChangePassword
  },
  {
    path:'/home',
    name:'home',
    component:Home,
    children:[
      {
        path:'/home/user',
        name:'UserManage',
        component:UserManage
      }
    ]
  }
]

const router = new VueRouter({
  mode: 'history',
  base: process.env.BASE_URL,
  routes
})

export default router
