<script setup lang="ts">
import { reactive, defineProps } from "vue";
import { useCounterStore } from "@/stores/counter";

const counter = useCounterStore();
const { GENDER, userUpdate } = counter;

const props = defineProps({
  user: {
    type: Object,
    default: () => ({}),
  },
});

//const emit = defineEmits(["update-success"]);
// Using reactive to manage form state
const formData = reactive({
  id: props.user.id || "",
  name: props.user.name || "",
  email: props.user.email || "",
  password: props.user.password || "",
  phoneNumber: props.user.phoneNumber || "",
  gender: props.user.gender || GENDER.UNDEFINED,
});

const updatedUser = async () => {
  try {
    const res = await userUpdate(formData);
    if (!res.message) {
      alert(res.error);
      return;
    }
    alert(res.message);
  } catch (error) {
    alert(error);
  }
};
</script>

<template>
  <h2 :style="{ textAlign: 'center' }">Update Page</h2>
  <div class="container">
    <form @submit.prevent="updatedUser">
      <div class="sub-container">
        <input
          type="text"
          placeholder="id.."
          class="form-check"
          v-model="formData.id"
          readonly
        />
      </div>
      <div class="sub-container">
        <input
          type="text"
          placeholder="name..."
          class="form-check"
          v-model="formData.name"
        />
      </div>
      <div class="sub-container">
        <input
          type="email"
          placeholder="email..."
          class="form-check"
          v-model="formData.email"
        />
      </div>
      <div class="sub-container">
        <input
          type="password"
          placeholder="password..."
          class="form-check"
          v-model="formData.password"
        />
      </div>
      <div class="sub-container">
        <input
          type="text"
          placeholder="phoneNumber..."
          class="form-check"
          v-model="formData.phoneNumber"
        />
      </div>
      <div class="sub-container">
        <label>GENDER : </label>
        <select v-model="formData.gender" :style="{ height: '50px' }">
          <option :value="GENDER.UNDEFINED">UNDEFINED</option>
          <option :value="GENDER.MALE">MALE</option>
          <option :value="GENDER.FEMALE">FEMALE</option>
        </select>
      </div>
      <button>Update</button>
    </form>
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
