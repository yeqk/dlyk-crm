import {createRouter, createWebHistory} from "vue-router";

const router = createRouter({
    history: createWebHistory(),
    routes: [
        {
            path:'/',
            component: () => import('../view/LoginView.vue'),
        },
        {
            path:'/dashboard',
            component: () => import('../view/DashboardView.vue'),
            children: [
                {
                    path:'user',
                    component: () => import('../view/UserView.vue'),
                },
                {
                    path:'user/:id',
                    component: () => import('../view/UserDetailView.vue'),
                }
            ],
        },
    ]})

export default router;