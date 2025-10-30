import './App.scss'
import {AppBarNoProbDrilling} from "./components/AppBar/AppBarNoProbDrilling.tsx"
import {AppBarPropDrilling} from "./components/AppBar/AppBarPropDrilling.tsx"
import UserContextProvider from "./context/UserContextProvider.tsx"
import {useContext} from "react"
import {UserContext} from "./context/UserContext.ts"

// this is a mock user object, would come from an API in a real app (after authentication)
const user = {
    name: "Anita Doe",
    avatar: "https://randomuser.me/api/portraits/women/75.jpg"
}

function WithoutContext() {
    return (
        <>
            <AppBarPropDrilling title={"Appbar"} user={user}/>
            <div>
                <h1>Prop drilling</h1>
            </div>
        </>
    )
}

function WithContext() {
    const {setUser} = useContext(UserContext)
    setUser(user)
    return (
        <>
            <AppBarNoProbDrilling title={"Appbar"}/>
            <div>
                <h1>Context</h1>
            </div>
        </>
    )
}

function App() {
    return (
        <div className="app">
            <WithoutContext/>
            <UserContextProvider>
                <WithContext/>
            </UserContextProvider>
        </div>
    )
}

export default App
