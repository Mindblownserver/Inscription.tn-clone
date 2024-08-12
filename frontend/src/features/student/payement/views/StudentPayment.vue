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
                            Content I
                        </div>
                    </div>
                    <div class="step-controls">
                        <Button label="Next"  severity="contrast" @click="activateCallback('2')" />
                    </div>
                </StepPanel>
                <StepPanel v-slot="{ activateCallback }" value="2">
                    <div class="panel-content">
                        <div class="content2">
                            <div class="verif">
                                <h2 style="margin-bottom: -10px;">Verifier la carte d'étudiant</h2>
                                <p>If there's some mistake or the photo didn't render correctly, please modify it in your <a href="">profile</a></p>
                            </div>
                            <CartEtudiantVerif />
                        </div>
                    </div>
                    <div class="step-controls">
                        <Button label="Back" severity="secondary" @click="activateCallback('1')" />
                        <Button label="Next" severity="contrast" @click="activateCallback('3')" />
                    </div>
                </StepPanel>
                <StepPanel v-slot="{ activateCallback }" value="3">
                    <div class="panel-content">
                        <div class="content3">Content III</div>
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
import CartEtudiantVerif from "../../components/CarteEtudiantVerif.vue"
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
    align-items: center;
    font-weight: 500;
    color: #333; /* Adjust as needed */
}
.content1, .content3{
    justify-content: center;
}
.content2{
    justify-content: start;
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
    gap: 0px;
}
.verif>p{
    font-weight: lighter;

}
</style>