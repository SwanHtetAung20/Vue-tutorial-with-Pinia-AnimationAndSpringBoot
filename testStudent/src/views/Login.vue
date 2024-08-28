<script setup lang="ts">
import { ref, computed, onMounted } from "vue";
import { useCounterStore } from "../stores/counter";
import { storeToRefs } from "pinia";
import router from "../router";
import { RouterLink } from "vue-router";

let email = ref("");
let password = ref("");

const counter = useCounterStore();
let { user } = storeToRefs(counter);
const { userLogin, isAuthenticated } = counter;

onMounted(() => {
  if (isAuthenticated) {
    router.push("/home");
  }
});

const loginHandler = async () => {
  if (email.value !== "") {
    try {
      const res = await userLogin(email.value, password.value);
      if (!res.user) {
        console.log(res);
        return;
      } else {
        console.log(user.value);
        user.value = res.user;
        localStorage.setItem("user", res.user);
        router.push("/home");
      }
    } catch (error) {
      alert(error);
    }
  } else {
    alert("Enter your email first!");
  }
};
</script>

<template>
  <h1 class="header-login">Login Here</h1>
  <div class="container">
    <form @submit.prevent="loginHandler">
      <div class="sub-container">
        <input
          type="email"
          placeholder="email.."
          class="form-check"
          v-model="email"
        />
      </div>
      <div class="sub-container">
        <input
          type="password"
          placeholder="password..."
          class="form-check"
          v-model="password"
        />
      </div>
      <button>Login</button>
    </form>
    <p>
      Haven't account?. Please
      <RouterLink to="/sign-up"
        ><strong class="signUp">sign up</strong></RouterLink
      >
      here
    </p>
  </div>
</template>
<style scoped>
.container,
.header-login {
  text-align: center;
}

.sub-container {
  padding: 10px;
}

.form-check {
  width: 400px;
  padding: 10px;
}
button {
  padding: 10px;
}

.signUp {
  cursor: pointer;
  color: green;
}
</style>
