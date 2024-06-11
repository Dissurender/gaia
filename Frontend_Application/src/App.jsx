
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
    <AuthProvider>
      {" "}
      {/* Wrap routes with AuthProvider */}
      <Routes>
        <Route path="/" element={<Home />} />
        <Route path="/login" element={<Login setUserInfo={setUserInfo} />} />
        <Route path="/register" element={<Register />} />
        <Route path="/dashboard" element={<Dashboard userInfo={userInfo} />} />
        <Route path="/myrecipes" element={<MyRecipes userInfo={userInfo} />} />
        <Route path="/mygrocerylist" element={<MyGroceryList userInfo={userInfo} />} />
        <Route path="/settings" element={<Settings userInfo={userInfo} />} />
      </Routes>
    </>
  )
}

export default App