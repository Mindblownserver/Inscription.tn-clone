import UniHome from "../home/views/UniHome.vue"
import UniStudents from "../students/views/UniStudents.vue"
export default [{
    path: "/university",
    name: "UniHome",
    component: UniHome
    },
    {
        path: "/university/students",
        name: "UniStudents",
        component: UniStudents
    }
]