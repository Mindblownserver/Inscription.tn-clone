<template>
    <FloatLabel class="btn">
        <InputText id="username" v-model="value" />
        <label for="username">Username</label>
    </FloatLabel>
</template>

<script setup lang="ts">
import FloatLabel from 'primevue/floatlabel';
import InputText from 'primevue/inputtext';
import { ref } from 'vue';
const value = ref(null)
</script>


<style scoped>
.btn{
    margin-bottom: 5vh;
}

</style>