import {myApi} from "@/service/MyApi";
import { Inscription } from "@/utilities/interfaces";
import { ActionContext } from "vuex";

interface State {
    loading: boolean;
    inscriptions: Inscription[];
    error: string | null;
}

export const uniModule={
    namespaced:true,
    state(): State {
        return {
          loading: false,
          inscriptions: [],
          error: null,
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

    },
    actions:{
        async getInscriptionByFac({commit}: ActionContext<State, any>, idEtab:string){
            try {
                commit("setLoading", true);
                const res = await myApi.getInscriptionsParFac<Array<Inscription>>(idEtab);
                commit("setInscriptions", res.data);
            } catch (error) {
                commit("setError", error);
            } finally {
                commit("setLoading", false);
            }
        },
        
    }
}