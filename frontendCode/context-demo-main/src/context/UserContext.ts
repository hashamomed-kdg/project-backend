import {createContext} from 'react'
import {User} from "../model/User.ts"

export interface UserContextType {
    user: User | null
    setUser: (user: User) => void
}

export const UserContext = createContext<UserContextType>({
    // the default value in a React Context is used in cases where a component consuming the context does not have a matching Provider in its component tree.
    user: null, //start value is null
    setUser: () => {
        // empty function will be replaced with real function in Provider
    },
})
