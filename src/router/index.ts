import { createRouter, createWebHistory, RouteRecordRaw } from 'vue-router'
import studentRoute from '@/features/student/router/studentRoute'
import uniRoute from '@/features/university/router/uniRoute'

const routes: Array<RouteRecordRaw> = [
  
  {
    path:'/signup',
    name:'SignUp',
    component:()=> import("../features/signup/views/sign_up.vue")
  },
  {
    path:'/login',
    name:'Login',
    component:()=> import("../features/login/views/login.vue")
  },
  ...studentRoute,
  ...uniRoute
]

const router = createRouter({
  history: createWebHistory(),
  routes
})

export default router
