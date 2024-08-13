import {myApi} from "@/service/MyApi";
import { ActionContext } from "vuex";
import { Student,Inscription } from "../utilities/interfaces";

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
        }
    },
    actions:{
        async getInscriptionByCin({commit}: ActionContext<State, any>, cin:string){
            try {
                const res = await myApi.getInscriptionByCin<Array<Inscription>>(cin);
                commit("setInscriptions", res.data);
                commit("setStudent", res.data[0].etu);
                console.log(res.data)
            } catch (error) {
                commit("setError", error);
            } finally {
                commit("setLoading", false);
            }
        }
    }

}