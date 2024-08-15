import { createRouter, createWebHistory, RouteLocationNormalized, RouteRecordRaw } from 'vue-router'
import studentRoute from '@/features/student/router/studentRoute'
import uniRoute from '@/features/university/router/uniRoute'
import adminRoute from "@/features/admin/router/adminRoute"
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
  {
    path:'/faq',
    name:'FAQ',
    component:()=> import("../features/FAQ/views/FAQ.vue")
  },
  ...studentRoute,
  ...uniRoute,
  ...adminRoute
]

const router = createRouter({
  history: createWebHistory(),
  routes
})

/* router.beforeEach((to, from, next) => {
  const isAuthenticated = !!localStorage.getItem("accessToken");

  if (to.matched.some(record=>record.meta.requireAuth)){
    if(!isAuthenticated){
      if(to.name!=="Login"){
        console.log("I'm here")
        next({name: "Login"})
      }
      else{
        next()
      }
    }else{
      next()
    }
  }else{
    next
  }
})
 */
/* 
switch (localStorage.getItem("role")) {
        case "student":
          return "/student"   
        case "university":
          return "/university"
        case "admin":
          return "/admin"   
        default:
          return "/login";
      }
*/
export default router
