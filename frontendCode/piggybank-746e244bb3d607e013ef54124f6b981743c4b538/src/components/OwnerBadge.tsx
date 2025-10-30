import './OwnerBadge.scss'
import type {Owner} from "../model/Owner.ts"

interface OwnerBadgeProps {
    owner: Owner
    onClick: () => void
}

export function OwnerBadge({owner, onClick}: OwnerBadgeProps) {
    return (
        <div className="owner-badge" title={owner.name} onClick={onClick}>
            <img src={owner.image} alt={owner.name}/>
        </div>
    )
}

