<template>
    <Card id="timeCard">
        <template #header>  
            <div class="iconHolder">
                <i class="material-icons-round opacity-10 fs-5 icon">person</i>
            </div>
        </template>
        <template #title>
            <div class="title">
                <span >
                    {{ props.title }}
                </span>
            </div>
            <span class="subtitle">
                {{props.value}}
            </span>
        </template>
        <template #footer>
            <Divider />
            <div class="footer">
                <i class="material-icons-round opacity-10 fs-5 trend">{{ props.icon }}</i>
                <span>{{ props.detail }}</span>
            </div>
        </template>
    </Card>
</template>
<script setup lang="ts">
import Card from 'primevue/card';
import Divider from 'primevue/divider';
import {ref} from "vue"

const props = defineProps({
    title:{
        type:String,
    },
    value:{
        type:String
    },
    detail:{
        type:String
    },
    icon:{
        type:String
    }
})
const Au= ref(null)
</script>

<style scoped>
.full-width {
  width: 100%;
}

/* Responsive styles */
@media (min-width: 768px) { /* md breakpoint in Tailwind is 768px */
  .md-width-56 {
    width: 14rem; /* Tailwind's 'w-56' is 14rem (56 * 0.25rem) */
  }
}
#timeCard{
    width: 24rem;
    position: relative;
    text-align: end;
}
.iconHolder{
  position: absolute;
  top: -24px;
  left: 16px;
  width: 48px;
  height: 48px;
  border-radius: 10px;
  display: flex;
  align-items: center;
  justify-content: center;
  font-size: 24px;
  background: linear-gradient(to right, rgb(33, 138, 243), rgb(14, 109, 233)); 
  box-shadow: 0px 4px 6px rgba(0, 0, 0, 0.1);
}
.icon{
    color: white;

}

.title {
  font-weight: 600;
  font-size: 16px;
  margin-bottom: 5px;
}
.subtitle {
  font-size: 20px;
  font-weight: bolder;
  margin: 0;
  color: black;
}
.footer{
    display: flex;
    justify-content: end;
    align-items: center;
    gap: 10px;
}
</style>