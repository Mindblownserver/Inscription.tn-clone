import StudentHome from "../home/views/StudentHome.vue"
import StudentPayment from "../payement/views/StudentPayment.vue"
import StudentProfile from "../profile/views/StudentProfile.vue"
export default [
    {
        path: "/student",
        name: "StudentHome",
        component: StudentHome
    },
    {
        path: "/student/payment",
        name: "StudentPayment",
        component: StudentPayment
    },
    {
        path: "/student/profile",
        name: "StudentProfile",
        component: StudentProfile
    }
]