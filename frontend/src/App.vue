<template>
  <nav v-if="route.path !== '/signup' && route.path !== '/login' ">
    <NavBar />
  </nav>
  <router-view/>
</template>
<script setup>
import NavBar from './features/navbar/components/NavBar.vue';
import { useRoute } from "vue-router";

const route = useRoute();
</script>
<style>
#app {
  font-family: Avenir, Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  text-align: center;
  
}

:root{
  --main-web-color: hsl(219, 75%, 50%);
  --web-bg: rgb(247, 247, 247);
}

nav {
  padding: 30px;
}

nav a {
  font-weight: bold;
  color: #2c3e50;
  text-decoration: none;
}

nav a.router-link-exact-active {
  color: #42b983;
}
body{
  background-color: var(--web-bg);
}
/* 
*:focus{
  outline: none;
} */
</style>
