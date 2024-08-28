<script setup lang="ts">
import gsap from "gsap";

const beforeEnter = (el) => {
  (el.style.opacity = 0), (el.style.transform = "translateY(-60px)");
};

const enter = (el, done) => {
  gsap.to(el, {
    duration: 2,
    y: 0,
    opacity: 1,
    ease: "bounce.out",
    onComplete: done,
    // delay:el.dataset.index * 0.2
    // you can use delay for list to render one after another
    // in el.dataset.index "index" is the name you set to the list like this :data-index="index"
  });
};

const afterEnter = (el) => {
  console.log("After enter");
};
</script>

<template>
  <div>
    <transition
      appear
      @before-enter="beforeEnter"
      @enter="enter"
      @after-enter="afterEnter"
    >
      <h1 :style="{ textAlign: 'center' }">About Page</h1>
    </transition>
    <div class="container-about">
      Lorem, ipsum dolor sit amet consectetur adipisicing elit. Similique
      delectus fugiat ea, expedita eaque quasi nam iure velit totam dolorum
      perspiciatis consequatur asperiores placeat? Rem est unde laborum nemo
      quasi. Lorem ipsum dolor sit amet consectetur adipisicing elit. Corrupti
      rerum nihil modi excepturi. Perferendis quod culpa quos molestias tenetur
      odio obcaecati. Voluptatum, suscipit? Voluptates, doloribus magnam magni
      cumque odio nesciunt? Lorem ipsum dolor sit amet consectetur adipisicing
      elit. Architecto molestias, sequi, velit nesciunt quos numquam minus non
      quisquam tenetur aliquid totam itaque cumque hic iure recusandae
      assumenda! Ullam, repudiandae sint?
    </div>
  </div>
</template>

<style scoped>
.container-about {
  text-align: center;
  padding: 100px;
}

/* .about-enter-from,
.about-leave-to {
  opacity: 0;
  transform: translateY(60px);
}

.about-enter-active,
.about-leave-active {
  transition: all 0.5s ease;
} */
</style>
