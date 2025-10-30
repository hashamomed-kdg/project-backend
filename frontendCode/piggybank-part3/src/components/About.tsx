import {Card, CardContent, CardMedia, Typography} from '@mui/material'
import Piggy from '../assets/img/piggy.png'

export default function About() {
    return (
        <Card sx={{maxWidth: 600, mx: 'auto', mt: '2rem'}}>
            <CardMedia component="img" image={Piggy} alt="about" sx={{p: 3}}/>
            <CardContent>
                <Typography gutterBottom variant="h5" component="div">
                    Piggybanks
                </Typography>
                <Typography variant="body2" color="text.secondary">
                    React demo project used in programming courses at Karel de Grote Hogeschool
                </Typography>
            </CardContent>
        </Card>
    )
}
