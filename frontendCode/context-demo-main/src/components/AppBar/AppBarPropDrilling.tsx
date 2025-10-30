import {User} from "../../model/User.ts"
import './AppBar.scss'

// in a real application, this components would be in separate files

function BatteryPanel() {
    const battery = 100 // should be a real value calculated from the device
    return (
        <div>
            <p>Battery: {battery}%</p>
        </div>
    )
}

interface UserPanelProps {
    user: User | null
}

function UserPanel({user}: UserPanelProps) {
    return (
        user && (
            <div className="user-panel">
                <img src={user.avatar} alt={user.name}/>
                <p>{user.name}</p>
            </div>
        )
    )
}


interface InfoPanelProps {
    user: User | null
}

function InfoPanel({user}: InfoPanelProps) {
    return (
        <div className="info-panel">
            <BatteryPanel/>
            <UserPanel user={user}/>
        </div>
    )
}

interface AppBarProps {
    title: string
    user: User | null
}

export function AppBarPropDrilling({title, user}: AppBarProps) {
    return (
        <div className="app-bar">
            <div>{title}</div>
            <InfoPanel user={user}/>
        </div>
    )
}