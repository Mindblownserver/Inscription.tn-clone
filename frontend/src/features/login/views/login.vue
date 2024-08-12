<template>
    <section>
        <div class="img"><img src="../../../assets/images/University Life Illustration.jpg" alt="login cover art">
        </div>
        <div class="content">
            <div class="form">
                <h2>Login to Insription.tn</h2>
                <form>
                    <InputText class="formInput" v-model="email" type="text" size="large" placeholder="Email" :invalid="!emailValidator.test(email)" />
                    <br>
                    <InputText class="formInput" v-model="password" type="password" size="large" placeholder="Password" />
                    <br>
                    <div class="formElement">
                        <p>Don't have an account? <a href="/signup">Sign up</a></p>
                    </div>
                    <Button class="btn" label="Login" severity="info" raised />
                </form>
            </div>
        </div>
    </section>
</template>

<script setup>
import { ref } from 'vue';
import InputText from 'primevue/inputtext';
import Button from 'primevue/button';

const email = ref("");
const password = ref("");
const emailValidator = new RegExp("^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$")
</script>

<style scoped>
*{
    margin: 0;
    padding: 0;
    box-sizing: border-box;
}
section{
    position: relative;
    width: 100%;
    height: 98vh;
    display: flex;
}
section .img{
    position: relative;
    width: 40%;
    height: 100%;
}
section .img:before{
    content: "";
    position: absolute;
    top: 0;
    left: 0;
    width: 100%;
    height: 100%;
    background-image: linear-gradient(to left, var(--main-web-color) 0%, rgb(56, 103, 246) 100%);
    z-index: 1;
    mix-blend-mode: screen;
}

section .img img{
    position: absolute;
    top: 0;
    left: 0;
    width: 100%;
    height: 100%;
    object-fit: cover;
}

section .content{
    display: flex;
    justify-content: center;
    align-items: center;
    width: 50%;
    height: 100%;
}
section .content .form{
    width: 50%;
    display: block;
}
h2{
    color: black;
    font-weight: bolder;
    font-size: 1.5rem;
    margin-bottom: 20px;
    display: inline-block;
    letter-spacing: 1px;
}
.formElement, .formInput{
    margin-block: 20px;
}

.formInput{
    width: 100%;
    padding: 10px 20px;
    outline: none;
    font-weight: 400;
    border: 1px solid rgb(39, 108, 228);
    font-size: 16px;
    letter-spacing: 1px;
    color: rgb(64, 64, 64);
    background: transparent;
}

.btn{
    color: white;
    font-weight: 500;
    width: 50%;
    padding: 10px 20px;
}

@media  (max-width: 900px) {
    section .img {
        position: absolute;
        top: 0;
        left: 0;
        width: 100%;
        height: 100%;
    }
    section .content{
        display: flex;
        justify-content: center;
        align-items: center;
        width: 100%;
        height: 100%;
        z-index: 1;
    }
    section .content .form{
        width: 100%;
        padding: 40px;
        background-color: rgb(255 255 255 / 0.9);
        margin: 50px;
        display: block;
    }
}
</style>