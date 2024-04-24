
import { Routes, Route } from 'react-router-dom'
import Layout from './layouts'

import Home from './pages/home'
import { Login, Register } from './pages/auth'
import Dashboard from './pages/dashboard'
import MyRecipes from './pages/favorites'
import MyGroceryList from './pages/grocerylist'
import Settings from './pages/settings'

import './App.css'

function App() {

  return (
    <>
       <Routes>
        <Route path="/" element={<Layout />}>
          <Route index element={<Home />} />
          <Route path="/login" element={<Login />} />
          <Route path="/register" element={<Register />} />
          <Route path="/dashboard" element={<Dashboard />} />
          <Route path="/myrecipes" element={<MyRecipes />} />
          <Route path="/mygrocerylist" element={<MyGroceryList />} />
          <Route path="/settings" element={<Settings />} />
          <Route path="*" element={<Home />} />
        </Route>
      </Routes>
    </>
  )
}

export default App