import { createRouter, createWebHistory } from 'vue-router'

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    {
      path: '/',
      name: 'login',
      component: () => import('../views/LoginView.vue')
    },
    {
      path: '/AdminView',
      name: 'AdminView',
      component: () => import('../views/AdminView.vue')
    },
    {
      path: '/AccountView',
      name: 'AccountView',
      component: () => import('../views/AccountView.vue')
    },
    {
      path: '/WorkflowView',
      name: 'WorkflowView',
      component: () => import('../views/WorkflowView.vue')
    },
    {
      path: '/VendorView',
      name: 'VendorView',
      component: () => import('../views/VendorView.vue')
    },
    {
      path: '/ApprovalView',
      name: 'ApprovalView',
      component: () => import('../views/ApprovalView.vue')
    },
    {
      path: '/VendorAssessmentForm',
      name: 'VendorAssessmentForm',
      component: () => import('../views/VendorAssessmentForm.vue')
    },
    {
      path: '/CreateAccount',
      name: 'CreateAccount',
      component: () => import('../views/CreateAccount.vue')
    }
  ]
})

export default router
