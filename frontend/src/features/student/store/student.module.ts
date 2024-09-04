import {myApi} from "@/service/MyApi";
import { ActionContext } from "vuex";
import { Student,Inscription } from "../../../utilities/interfaces";

interface State {
    loading: boolean;
    inscriptions: Inscription[];
    error: string | null;
    student: Student| null; 
}


export const studentModule={
    namespaced:true,
    state(): State {
        return {
          loading: false,
          inscriptions: [],
          error: null,
          student: null // Initialize as null or an appropriate default value
        };
    },
    mutations:{
        setLoading(state:State,loading:boolean){
            state.loading = loading
        },
        setError(state:any,err:string){
            state.error =err;
        },
        setInscriptions(state:State,inscriptions: Array<Inscription>){
            state.inscriptions=inscriptions;
        },
        setStudent(state:State,student: Student){
            state.student=student;
        },
        setStudentImage(state:State, base64Image: string){
            if(state.student)
                state.student.photoEtu=base64Image;
        }
    },
    actions:{
        async getInscriptionByCin({commit}: ActionContext<State, any>, cin:string){
            try {
                const res = await myApi.getInscriptionByCin<Array<Inscription>>(cin);
                commit("setInscriptions", res.data);
            } catch (error) {
                commit("setError", error);
                throw error;
            } finally {
                commit("setLoading", false);
            }
        },

        async getStudentByCin({commit}: ActionContext<State, any>, cin:string){
            try {
                const res = await myApi.getStudentByCin<Array<Student>>(cin);
                commit("setStudent", res.data);
            } catch (error) {
                commit("setError", error);
                throw error;
            } finally {
                commit("setLoading", false);
            }
        },
        async updateStudent({commit}: ActionContext<State, any>, stu: Student){
            try{
                commit("setStudent", stu);
                await myApi.updateStudent(stu);
                console.log("Updated");
            }catch (error) {
                commit("setError", error);
                throw error;
                
            }
        }
    }
}