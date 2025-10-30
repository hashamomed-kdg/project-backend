import {ReactNode, useState} from 'react'
import {UserContext} from './UserContext.ts'
import {User} from "../model/User.ts"


interface UserContextProviderProps {
    children: ReactNode
}

export default function UserContextProvider({children}: UserContextProviderProps) {
    const [user, setUser] = useState<User | null>(null)

    return (
        <UserContext.Provider value={
            {
                user: user,
                setUser: setUser
            }
        }>
            {children}
        </UserContext.Provider>
    )
}
