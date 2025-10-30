import {Avatar, Divider, IconButton, Stack} from "@mui/material"
import MenuIcon from "@mui/icons-material/Menu"

interface HeaderProps {
    onMenuClick: () => void
}

export function Header({onMenuClick}: HeaderProps) {
    return (
        <Stack sx={{
            mb: {
                xs: '1rem',
                md: '3rem',
            }
        }}>
            <Stack direction="row"
                   sx={{p: 2, justifyContent: 'space-between', backgroundColor: 'background.paper'}}>
                <IconButton onClick={onMenuClick}>
                    <MenuIcon/>
                </IconButton>
                <Avatar // Temp hardcoded user avatar
                    alt="Logged in user"
                    src={`/img/users/6.png`}
                    sx={{width: '2.8rem', height: '2.8rem'}}
                />
            </Stack>
            <Divider/>
        </Stack>)

}