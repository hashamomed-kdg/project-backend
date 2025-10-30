import {Drawer, List, ListItem, ListItemButton, ListItemIcon, ListItemText} from '@mui/material'
import SavingsIcon from '@mui/icons-material/Savings'
import AboutIcon from '@mui/icons-material/InfoOutlined'
import {Link} from "react-router"

interface NavigationProps {
    isOpen: boolean
    onClose: () => void
}

export function Navigation({isOpen, onClose}: NavigationProps) {
    return (
        <Drawer open={isOpen} onClose={onClose}>
            <List sx={{width: 200}}>
                {[
                    {label: 'Piggybanks', link: '/', icon: <SavingsIcon/>},
                    {label: 'About', link: '/about', icon: <AboutIcon/>},
                ].map((menuItem) => (
                    <ListItem disableGutters key={menuItem.link}>
                        <ListItemButton component={Link} to={menuItem.link} onClick={onClose}>
                            <ListItemIcon>{menuItem.icon}</ListItemIcon>
                            <ListItemText primary={menuItem.label}/>
                        </ListItemButton>
                    </ListItem>
                ))}
            </List>
        </Drawer>
    )
}
