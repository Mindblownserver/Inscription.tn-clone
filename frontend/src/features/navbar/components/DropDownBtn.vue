<template>
    <Button class="btn" label="Compte" text severity="danger" iconPos="right">
        <template #icon>
            <i class="material-icons-round opacity-10 fs-5">account_circle</i>
        </template>
    </Button>

    <div class="dropdown-content">
        <h5 style="margin-top: 20px;">{{userData.id}}</h5>
        <h5>{{ userData.name }}</h5>
        <hr>
        <Button label="Log out" text severity="danger" @click="logout">
          <template #icon>
            <i class="material-icons-round opacity-10 fs-5" style="margin-right: 20px">logout</i>
          </template>
        </Button>
    </div>
</template>

<script setup lang="ts">
import { myApi } from '@/service/MyApi';
import UserInfo from '@/service/authService';
import Button from 'primevue/button';
import { useRouter } from 'vue-router';

const props = defineProps<{userData: UserInfo}>();
const router = useRouter();

const logout= ()=>{
  myApi.logout();
  router.push({path:"/login"})
}
</script>
<style scoped>
.dropdown-content {
  display: block;
  border-radius: 5px;
  position: absolute;
  background-color: #f9f9f9;
  min-width: 300px;
  box-shadow: 0px 8px 16px 0px rgba(0,0,0,0.2);
  z-index: 1;
  opacity: 0;
  transform: translateX(-190px) translateY(-20px);
  transition: all .9s cubic-bezier(0.25, 1.03, 0.8, 0.92);
}
.dropdown-content a {
  color: black;
  border-radius: 5px;
  text-decoration: none;
  padding: 12px 15px;
  display: flex;
  text-align: left;
  align-items: center;
  cursor: pointer;
}

.dropdown-content a:hover {
  background-color: #ddd;
}

.btn:focus ~ .dropdown-content {
  opacity: 100%;
  transform: translateX(-190px) translateY(0);
}

</style>