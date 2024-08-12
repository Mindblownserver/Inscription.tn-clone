import { createApp } from 'vue'
import App from './App.vue'
import router from './router'
import store from './store'
import Aura from '@primevue/themes/aura';
import PrimeVue from 'primevue/config';

createApp(App).use(store).use(router).use(PrimeVue,{
    theme: {
    preset: Aura,
    options:{
        darkModeSelector:'.dark-version', 
        }
    }
}).mount('#app')
