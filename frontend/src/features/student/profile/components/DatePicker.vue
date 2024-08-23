<template>
    
<FloatLabel>
    <DatePicker :modelValue="props.date" inputId="birth_date" @input="updateDate" />
    <label for="birth_date">Birth Date</label>
</FloatLabel>
</template>

<script setup lang=ts>
import DatePicker from 'primevue/datepicker';
import FloatLabel from 'primevue/floatlabel';
const props = defineProps({
    date:{
        type:String
    }
});

const emit = defineEmits(['update:date']);

const updateDate = (event:any) => {
  emit('update:date', event.target.value);
};
</script>