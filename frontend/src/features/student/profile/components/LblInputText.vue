<template>
    <FloatLabel class="btn">
        <InputText id="username" :modelValue="props.value" :disabled="props.disabled" @input="updateValue"/>
        <label for="username">{{props.label}}</label>
    </FloatLabel>
</template>

<script setup lang="ts">
import FloatLabel from 'primevue/floatlabel';
import InputText from 'primevue/inputtext';
const props= defineProps({
    label:{
        type:String,
        required:true
    },
    disabled:{
        type:Boolean,
        default:false
    },
    value:{
        type:String,
    }
})
const emit = defineEmits(['update:value']);

const updateValue = (event:any) => {
  emit('update:value', event.target.value);
};

</script>


<style scoped>
.btn{
    margin-bottom: 5vh;
}

</style>