<template>
    <div >
        <Stepper value="1" linear>
            <StepList>
                <Step value="1">Consulter</Step>
                <Step value="2">Verifier Carte Etudiant</Step>
                <Step value="3">Payer</Step>
            </StepList>
            <StepPanels>
                <StepPanel v-slot="{ activateCallback }" value="1">
                    <div  class="panel-content">
                        <div class="content1">
                            <DataTable
                            paginator :rows="10" :loading="getLoading" :value="getInscriptions" 
                            size="large" :stripedRows="true" tableStyle="min-width: 50rem;" 
                            v-model:filters="filters" filterDisplay="row"
                            >
                                <template #empty> Aucune station trouvée </template>
                                <template #loading> Veuillez patienter </template>
                                <Column v-for="col of columns" sortable :key="col.field" :field="col.field"  :header="col.header">
                                <template #filter="{ filterModel, filterCallback }">
                                    <InputText v-model="filterModel.value" type="text" @input="filterCallback()" placeholder="Search by name" />
                                </template>
                                </Column>
                                <Column header="Nature du prix">
                                    <template #body="{data, index}">
                                        <Select :id="'select-'+index" :options="['Total', 'Par tranches']" placeholder="Choisir le type de paiement" 
                                        :highlightOnSelect="false" @change="loadPrix(index, $event, data.fill_etab.prixTr1, data.fill_etab.prixTotal)"
                                        :disabled="data.paid"/>
                                    </template>
                                </Column>
                                <Column header="Prix">
                                    <template #body="{data, index}">
                                        <span><b :id="'prix-'+index"></b></span>
                                    </template>
                                </Column>
                                <Column>
                                    <template #body="{data}">
                                        <Button label="Payer" severity="success" raised @click="()=>{activateCallback('2');payerToVerifCarte(data)}"
                                        :disabled="data.paid||!isSelected"/>
                                    </template>
                                </Column>
                                
                            </DataTable>
                        </div>
                    </div>
                </StepPanel>
                <StepPanel v-slot="{ activateCallback }" value="2">
                    <div class="panel-content">
                        <div class="content2">
                            <div class="verif">
                                <h2 style="margin-bottom: -10px;">Verifier la carte d'étudiant</h2>
                                <p>If there's some mistake or the photo didn't render correctly, please modify it in your <a href="/student/profile">profile</a></p>
                            </div>
                            <CartEtudiantVerif ref="carteEtu"/>
                            <div class="buttonLayout">
                                <Button label="Precedant" severity="secondary" @click="activateCallback('1')" />
                                <Button label="Suivant" severity="success" @click="activateCallback('3')" />
                            </div>
                        </div>
                    </div>
                </StepPanel>
                <StepPanel v-slot="{ activateCallback }" value="3">
                    <div class="panel-content">
                        <div class="content3">
                            <Button style="margin-left: 10px;" icon-pos="left" label="Payer" severity="warn" text> 
                                <template #icon>
                                    <i class="material-icons-round opacity-10 fs-5">credit_card</i>
                                </template>
                            </Button>
                            <div class="tagContent">
                                <Tag severity="info" value="Carte E-dinar/jeune">
                                    <template #icon>
                                        <i class="material-icons-round opacity-10 fs-5">check_circle</i>
                                    </template>
                                </Tag>
                                <Tag severity="success" value="Carte Zitouna">
                                    <template #icon>
                                        <i class="material-icons-round opacity-10 fs-5">check_circle</i>
                                    </template>
                                </Tag>
                                <Tag severity="info" value="Carte STB">
                                    <template #icon>
                                        <i class="material-icons-round opacity-10 fs-5">check_circle</i>
                                    </template>
                                </Tag>
                                <Tag severity="success" value="Carte BIAT">
                                    <template #icon>
                                        <i class="material-icons-round opacity-10 fs-5">check_circle</i>
                                    </template>
                                </Tag>
                                <Tag severity="success" value="Carte UIB">
                                    <template #icon>
                                        <i class="material-icons-round opacity-10 fs-5">check_circle</i>
                                    </template>
                                </Tag>
                            </div>
                        </div>
                    </div>
                    <div class="step-controls">
                        <Button label="Back" severity="secondary" icon="pi pi-arrow-left" @click="activateCallback('2')" />
                    </div>
                </StepPanel>
            </StepPanels>
        </Stepper>
    </div>
</template>



<script setup lang="ts">

import Stepper from 'primevue/stepper';
import StepList from 'primevue/steplist';
import StepPanels from 'primevue/steppanels';
import Step from 'primevue/step';
import StepPanel from 'primevue/steppanel';
import Button from 'primevue/button';
import DataTable from 'primevue/datatable';
import Column from 'primevue/column';
import Select from 'primevue/select';
import Tag from 'primevue/tag';
import { FilterMatchMode } from '@primevue/core/api';
import CartEtudiantVerif from "../../components/CarteEtudiantVerif.vue"
import { useStore } from 'vuex';
import { computed, onMounted, ref } from 'vue';
import { Inscription } from '../../../../utilities/interfaces';
import { tokenToId } from '@/service/tokenDecryptor';
import { useRouter } from 'vue-router';
import { isRefreshTokenError } from '@/service/authService';
import { showError } from '@/service/myToastService';
import { useToast } from 'primevue/usetoast';
import { myApi } from '@/service/MyApi';

interface CarteEtu {
  loadCardInfo: (data: any) => void;
}

const isSelected = ref(false);
const carteEtu = ref<CarteEtu | null>(null);

const store = useStore();
const router = useRouter();
const toast = useToast();

const columns = [
  { field: 'au', header: 'AU' },
  { field: 'fill_etab.fill.nomFrFill', header: 'Filliere' },
  { field: 'fill_etab.etab.nomFrEtab', header: 'Etablissement' },
  /* { field: 'nomFr', header: 'Nature Prix' },
  { field: 'nomAr', header: 'Prix' }, */
]

const filters = {
    au: { value: null, matchMode: FilterMatchMode.EQUALS },
  'fill_etab.fill.nomFrFill': { value: null, matchMode: FilterMatchMode.STARTS_WITH },
  'fill_etab.etab.nomFrEtab': { value: null, matchMode: FilterMatchMode.STARTS_WITH },

}

const getLoading = computed(()=>store.state.studentModule.loading);
const getInscriptions = computed(()=>store.state.studentModule.inscriptions);

const loadPrix= function(index: number, event:any, prixTr:number, prixTotal:number){
    let priceElement:any=document.getElementById("prix-"+index);
    let selectPrice:any=document.getElementById("select-"+index);
    const totalPrixSelection:string=`<span class="p-select-label" tabindex="0" role="combobox" aria-label="Total" aria-haspopup="listbox" aria-expanded="false" aria-controls="select-1_list" aria-disabled="0" data-pc-section="label">Total</span><!----><div class="p-select-dropdown" data-pc-section="dropdown"><svg width="14" height="14" viewBox="0 0 14 14" fill="none" xmlns="http://www.w3.org/2000/svg" class="p-icon p-select-dropdown-icon" aria-hidden="true" data-pc-section="dropdownicon"><path d="M7.01744 10.398C6.91269 10.3985 6.8089 10.378 6.71215 10.3379C6.61541 10.2977 6.52766 10.2386 6.45405 10.1641L1.13907 4.84913C1.03306 4.69404 0.985221 4.5065 1.00399 4.31958C1.02276 4.13266 1.10693 3.95838 1.24166 3.82747C1.37639 3.69655 1.55301 3.61742 1.74039 3.60402C1.92777 3.59062 2.11386 3.64382 2.26584 3.75424L7.01744 8.47394L11.769 3.75424C11.9189 3.65709 12.097 3.61306 12.2748 3.62921C12.4527 3.64535 12.6199 3.72073 12.7498 3.84328C12.8797 3.96582 12.9647 4.12842 12.9912 4.30502C13.0177 4.48162 12.9841 4.662 12.8958 4.81724L7.58083 10.1322C7.50996 10.2125 7.42344 10.2775 7.32656 10.3232C7.22968 10.3689 7.12449 10.3944 7.01744 10.398Z" fill="currentColor"></path></svg></div><!--teleport start--><!--teleport end-->`;
    const parTranchePrixSelection:string=`<span class="p-select-label" tabindex="0" role="combobox" aria-label="Total" aria-haspopup="listbox" aria-expanded="false" aria-controls="select-1_list" aria-disabled="0" data-pc-section="label">Par Tranche</span><!----><div class="p-select-dropdown" data-pc-section="dropdown"><svg width="14" height="14" viewBox="0 0 14 14" fill="none" xmlns="http://www.w3.org/2000/svg" class="p-icon p-select-dropdown-icon" aria-hidden="true" data-pc-section="dropdownicon"><path d="M7.01744 10.398C6.91269 10.3985 6.8089 10.378 6.71215 10.3379C6.61541 10.2977 6.52766 10.2386 6.45405 10.1641L1.13907 4.84913C1.03306 4.69404 0.985221 4.5065 1.00399 4.31958C1.02276 4.13266 1.10693 3.95838 1.24166 3.82747C1.37639 3.69655 1.55301 3.61742 1.74039 3.60402C1.92777 3.59062 2.11386 3.64382 2.26584 3.75424L7.01744 8.47394L11.769 3.75424C11.9189 3.65709 12.097 3.61306 12.2748 3.62921C12.4527 3.64535 12.6199 3.72073 12.7498 3.84328C12.8797 3.96582 12.9647 4.12842 12.9912 4.30502C13.0177 4.48162 12.9841 4.662 12.8958 4.81724L7.58083 10.1322C7.50996 10.2125 7.42344 10.2775 7.32656 10.3232C7.22968 10.3689 7.12449 10.3944 7.01744 10.398Z" fill="currentColor"></path></svg></div><!--teleport start--><!--teleport end-->`;
    isSelected.value=true;
    if(event.value=="Total"){
        if(priceElement){
            priceElement.textContent=prixTotal;
            if(selectPrice)
                selectPrice.innerHTML=totalPrixSelection

        }
    }
    else{
        if(priceElement){
            priceElement.textContent=prixTr;
            if(selectPrice)
                selectPrice.innerHTML=parTranchePrixSelection
        }
    }
}  
const payerToVerifCarte = function(data:Inscription){
    if(carteEtu.value)
        carteEtu.value.loadCardInfo(data);
}
onMounted(()=>{
    store.dispatch("studentModule/getInscriptionByCin", tokenToId(localStorage.getItem("accessToken")))
    .catch((error)=>{
        if(isRefreshTokenError(error as Error)){
            showError(toast, error.message, "We're redirecting to login page")
            myApi.logout()
            router.push("/login");
        }
        else
          console.error("Navbar: ",error);
    })
})
</script>

<style scoped>
Stepper {
    display: flex;
    flex-direction: column;
}

StepList {
    display: flex;
    justify-content: space-between;
}

Step {
    flex: 1;
    text-align: center;
    padding: 10px;
    cursor: pointer;
    border-bottom: 2px solid #ccc;
    background-color: #f9f9f9;
}


/* Step Panels Component */
StepPanels {
    display: none; /* Hide all panels initially */
}

StepPanel {
    display: none; /* Hide all panels initially */
    flex-direction: column;
}

StepPanel.active {
    display: flex; /* Show the active panel */
}

/* Panel Content */
.panel-content {
    display: flex;
    flex-direction: column;
    height: 30rem; /* Equivalent to Tailwind's 48 */
}

.content1,.content3,.content2 {
    border: 2px dashed #ccc; /* Border color */
    border-radius: 8px;
    background-color: #f9f9f9;
    flex: 1;
    display: flex;
    font-weight: 500;
    color: #333; /* Adjust as needed */
}
.content1{
    justify-content: center;
    align-items: center;
    flex-direction: column;

}

.content2{
    flex-direction: column;
    justify-content: start;
}

.content3{
    flex-direction: column;
    justify-content: center;
    align-items: start;
}
.tagContent{
    margin-top: 10px;
    display: flex;
    gap: 10px;
    margin-left: 10px;
}
/* Step Controls */
.step-controls {
    display: flex;
    justify-content: flex-end;
    padding-top: 1.5rem; /* Equivalent to Tailwind's 6 */
}

.step-controls Button.severity-secondary {
    background-color: #6c757d; /* Secondary button color */
}

.step-controls Button:not(.severity-secondary) {
    margin-left: 0.5rem;
}

/* Dark Mode (adjust based on your preferences) */
body.dark-mode .content {
    border-color: #444;
    background-color: #333;
    color: #eee;
}

.verif{
    display: flex;
    text-align: start;
    flex-direction: column;
    gap: 0px;
    margin: 10px;
}
.verif>p{
    font-weight: lighter;
}
.buttonLayout{
    display: flex;
    justify-content: start;
    margin-left: 10px;
    gap: 10px;
}
</style>