<script setup lang="ts">
import { RouterView, RouterLink } from "vue-router";
</script>

<template>
  <RouterLink to="/home">Home</RouterLink>
  <RouterLink to="/about">About</RouterLink>
  <RouterView v-slot="{ Component }">
    <transition name="route" mode="out-in">
      <component :is="Component"> </component>
    </transition>
  </RouterView>
</template>

<style scoped>
.route-enter-from {
  opacity: 0;
  transform: translateX(100px);
}
.route-enter-active {
  transition: all 0.3s ease-out;
}

.route-leave-to {
  opacity: 0;
  transform: translateX(-100px);
}

.route-leave-active {
  transition: all 0.3s ease-in;
}
</style>
