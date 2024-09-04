import { studentModule } from '@/features/student/store/student.module'
import { uniModule } from '@/features/university/store/university.module'
import { createStore } from 'vuex'

export default createStore({
  state: {    
  },
  getters: {
  },
  mutations: {
  },
  actions: {
  },
  modules: {
    studentModule,
    uniModule
  }
})
