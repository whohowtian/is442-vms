<template>
    <button class="my-button" :class="[buttonStyle === 'clear' ? 'clear' : buttonStyle === 'solid' ? 'solid' : 'none']" @click="$emit('click')">
      <slot></slot>
    </button>
</template>
  
<script>
  export default {
    name: 'Button',
    props: {
      buttonStyle: {type: String,default: 'clear'}
    }
  }
</script>
  
<style>
  .my-button {
    border: none;
    padding: 0.3rem;
    border-radius: 4px;
    cursor: pointer;
  }

  .clear {
    background-color: #fff;
    color: #0079B3;
    border: 2px solid #007bff;
    font-weight: bold;;

  }
  
  .solid {
    background-color: #fff;
    color: #007bff;
    font-weight: bold;
  }
  .none {
    color: #007bff;
    background-color: #fff;

    font-weight: bold;
  }
  </style>
  