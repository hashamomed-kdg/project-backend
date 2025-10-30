import './AppBar.scss'
import {useContext} from "react"
import {UserContext} from "../../context/UserContext.ts"

// in a real application, this components would be in separate files

function BatteryPanel() {
    const battery = 100 // should be a real value calculated from the device
    return (
        <div>
            <p>Battery: {battery}%</p>
        </div>
    )
}


function UserPanel() {
    const {user} = useContext(UserContext)
    return (
        user && (
            <div className="user-panel">
                <img src={user.avatar} alt={user.name}/>
                <p>{user.name}</p>
            </div>
        )
    )
}


function InfoPanel() {
    return (
        <div className="info-panel">
            <BatteryPanel/>
            <UserPanel/>
        </div>
    )
}

interface AppBarProps {
    title: string
}

export function AppBarNoProbDrilling({title}: AppBarProps) {
    return (
        <div className="app-bar">
            <div>{title}</div>
            <InfoPanel/>
        </div>
    )
}