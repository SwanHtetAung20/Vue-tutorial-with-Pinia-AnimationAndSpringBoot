<script setup lang="ts">
import { ref } from "vue";
import { storeToRefs } from "pinia";
import { useCounterStore } from "../stores/counter";
import { RouterLink } from "vue-router";
import router from "@/router";

const counter = useCounterStore();

const { user, isAdd } = storeToRefs(counter);

const { GENDER, userSignUp } = counter;

let email = ref("");
let password = ref("");
let gender = ref(GENDER.UNDEFINED);

const signUpHandler = async () => {
  if (email.value === "") {
    alert("Email cannot be null!");
    return;
  }
  try {
    const myObj = {
      email: email.value,
      password: password.value,
      gender: gender.value,
    };
    const res = await userSignUp(myObj);
    if (!res.user) {
      alert(res.error);
      return;
    } else {
      user.value = res.user;
      var loginUser = localStorage.getItem("user");
      if (!loginUser) {
        localStorage.setItem("user", res.user);
        router.push("/home");
      } else {
        isAdd.value = false;
      }
    }
  } catch (error) {
    alert(error);
  }
};
</script>

<template>
  <h1 class="header-login">Sign Up Here</h1>
  <div class="container">
    <form @submit.prevent="signUpHandler">
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
      <div class="sub-container">
        <label>GENDER : </label>
        <select v-model="gender" :style="{ height: '50px' }">
          <option :value="GENDER.UNDEFINED" selected>UNDEFINED</option>
          <option :value="GENDER.MALE">MALE</option>
          <option :value="GENDER.FEMALE">FEMALE</option>
        </select>
      </div>
      <button>Sign Up</button>
    </form>
    <p>
      Already have account?. Back to
      <RouterLink to="/"><strong class="signUp">Login</strong></RouterLink>
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
