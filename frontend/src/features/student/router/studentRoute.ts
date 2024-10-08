import StudentHome from "../home/views/StudentHome.vue"
import StudentPayment from "../payement/views/StudentPayment.vue"
import StudentProfile from "../profile/views/StudentProfile.vue"
export default [
    {
        path: "/student",
        name: "StudentHome",
        component: StudentHome,
        meta: {requireAuth:true, role:"student"}
    },
    {
        path: "/student/payment",
        name: "StudentPayment",
        component: StudentPayment,
        meta: {requireAuth:true, role:"student"}
    },
    {
        path: "/student/profile",
        name: "StudentProfile",
        component: StudentProfile,
        meta: {requireAuth:true, role:"student"}
    }
]