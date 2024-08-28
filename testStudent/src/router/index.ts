import { createRouter, createWebHistory } from "vue-router";
import Login from "../views/Login.vue";
import SignUp from "../views/SignUp.vue";
import HomePage from "../views/HomePage.vue";
import NotFound from "@/views/NotFound.vue";
import About from "@/views/About.vue";

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    {
      path: "/",
      name: "login",
      component: Login,
    },
    {
      path: "/sign-up",
      name: "signUp",
      component: SignUp,
    },
    {
      path: "/home",
      name: "home",
      component: HomePage,
    },
    {
      path: "/about",
      name: "about",
      component: About,
    },
    {
      path: "/:catchAll(.*)",
      name: "notFound",
      component: NotFound,
    },
  ],
});

export default router;
