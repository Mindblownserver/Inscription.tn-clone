<template>
    <Button class="btn" label="Compte" text severity="danger" iconPos="right">
        <template #icon>
            <i class="material-icons-round opacity-10 fs-5">account_circle</i>
        </template>
    </Button>

    <div class="dropdown-content">
        <h5 style="margin-top: 20px;">11999019</h5>
        <p style="color: silver;">med.yassine.kharrat@gmail.com</p>
        <hr>
        <a><i class="material-icons-round opacity-10 fs-5" style="margin-right: 20px">logout</i>Log out</a>
    </div>
</template>

<script setup>
import Button from 'primevue/button';

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